import fs from 'fs/promises';
import path from 'path';

const geoDir = path.join(process.cwd(), 'backend', 'src', 'main', 'resources', 'geo');

// Fallback exact names because the files we download have specific formatting
const brazilOverrides = {
    "Amapa (Brasil)": "Amapá",
    "Ceara (Brasil)": "Ceará",
    "Espirito Santo (Brasil)": "Espírito Santo",
    "Goias (Brasil)": "Goiás",
    "Maranhao (Brasil)": "Maranhão",
    "Para (Brasil)": "Pará",
    "Paraiba (Brasil)": "Paraíba",
    "Parana (Brasil)": "Paraná",
    "Piaui (Brasil)": "Piauí",
    "Rondonia (Brasil)": "Rondônia",
    "Sao Paulo (Brasil)": "São Paulo"
};

async function main() {
    console.log("Downloading master GeoJSON for Brazil States...");
    const brRes = await fetch("https://raw.githubusercontent.com/codeforamerica/click_that_hood/master/public/data/brazil-states.geojson");
    if(!brRes.ok) throw new Error("Failed to fetch Brazilian states");
    const brData = await brRes.json();

    console.log("Downloading master GeoJSON for World Countries...");
    const worldRes = await fetch("https://raw.githubusercontent.com/johan/world.geo.json/master/countries.geo.json");
    if(!worldRes.ok) throw new Error("Failed to fetch world countries");
    const worldData = await worldRes.json();

    let files = await fs.readdir(geoDir);
    files = files.filter(f => f.endsWith('.geojson') && f !== 'monitoring_area.geojson');

    for (const file of files) {
        if (file === 'Parara.geojson') continue;

        const filePath = path.join(geoDir, file);
        const content = await fs.readFile(filePath, 'utf8');
        let geojson;
        try {
            geojson = JSON.parse(content);
        } catch(e) {
            console.error(`Invalid JSON in ${file}`);
            continue;
        }

        let regionName = "Unknown";
        if (geojson.features && geojson.features.length > 0 && geojson.features[0].properties && geojson.features[0].properties.name) {
            regionName = geojson.features[0].properties.name;
        } else {
            console.warn(`No region name found in ${file}. Skipping.`);
            continue;
        }

        let newGeometry = null;

        if (regionName.includes('(Brasil)')) {
            // Find in Brazil data
            let searchName = regionName.replace(' (Brasil)', '');
            if (brazilOverrides[regionName]) {
                searchName = brazilOverrides[regionName];
            }
            
            const feature = brData.features.find(f => f.properties.name === searchName);
            if (feature) {
                newGeometry = feature.geometry;
            }
        } else {
            // Find in World data
            const feature = worldData.features.find(f => f.properties.name === regionName);
            if (feature) {
                newGeometry = feature.geometry;
            }
        }

        if (newGeometry) {
             const newGeoJson = {
                 type: "FeatureCollection",
                 features: [
                     {
                         type: "Feature",
                         properties: { name: regionName },
                         geometry: newGeometry
                     }
                 ]
             };
             await fs.writeFile(filePath, JSON.stringify(newGeoJson, null, 2));
             console.log(`✅ Updated ${file}: ${regionName}`);
        } else {
             console.error(`❌ Could not find geometry for ${regionName} (${file})`);
        }
    }
}

main().catch(console.error);
