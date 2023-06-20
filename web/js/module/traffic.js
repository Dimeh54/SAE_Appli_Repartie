import loader from "./loader.js";

/**
 * Module correspondant à la gestion du traffic
 */

var markers_traffic = [];

/**
 * Créer un tab d'objet qui contiennent les infos dont on a besoin
 */
async function creerTabTraffic() {
    let url = "https://carto.g-ny.org/data/cifs/cifs_waze_v2.json";

    let tab_traffic = await loader.load_ressource(url);

    return tab_traffic.incidents;
}

/**
 * Affiche les markers des incidents sur la carte
 * @param tableau avec les infos de chaque incident
 * @param map la carte
 */
function displayOnMap(map, tab_traffic) {
    // Créer une icône personnalisée
    var customIcon = L.icon({
        iconUrl: '../Ressources/marker-danger.png',
        iconSize: [22, 32],
        iconAnchor: [20, 40],
        popupAnchor: [0, -40]
    });

    for (let t of tab_traffic) {
        // si polyline est un tableau de plus d'un élément
        if (Array.isArray(t.location.polyline) && t.location.polyline.length > 1) {
            // si l'attribut polyline est un vrai polyline (plusieurs coordonnées)
            //let polyline = L.polyline(t.location.polyline, {color: "red"}).addTo(map);
            continue;
        }
        let latlngs = t.location.polyline.split(" ");
        let marker = L.marker(latlngs, { icon: customIcon }).addTo(map);

        // Formatage de la date
        var dateObj = new Date(t.endtime);
        var formattedDate = dateObj.getDate() + "/" + (dateObj.getMonth() + 1) + "/" + dateObj.getFullYear();

        // Popup du marker
        marker.bindPopup("<b>"+t.short_description
            +"</b><p>"+t.location.street
            +"</p><p>Date de fin prévu : "+formattedDate+"</p>");

        markers_traffic.push(marker);
    }
    let layerTraffic = L.layerGroup(markers_traffic);
    layerTraffic.addTo(map);
    return layerTraffic;
}

export default { creerTabTraffic, displayOnMap, markers_traffic };