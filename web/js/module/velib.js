import loader from "./loader.js";

/**
 * Module correspondant à la gestion des vélibs
 */

var markers_velib = [];

/**
 * Affiche les markers des stations vélib sur la carte
 * @param tableau avec les infos de chaque station
 */
function displayOnMap(map, tab_velib) {
    // Créer une icône personnalisée
    var customIcon = L.icon({
        iconUrl: '../Ressources/velib.png',
        iconSize: [32, 34],
        iconAnchor: [20, 40],
        popupAnchor: [0, -40]
    });
    for (var i = 0; i < tab_velib.length; i++) {
        var marker = L.marker([tab_velib[i].lat, tab_velib[i].lon], { icon: customIcon }).addTo(map);

        // Popup du marker
        marker.bindPopup("<b>"+tab_velib[i].name
            +"</b><br>"+tab_velib[i].address
            +"<br>Vélos dispo : "+tab_velib[i].num_bikes_available
            +"<br>Places parking dispo : "+tab_velib[i].num_docks_available);

        // On récupère les markers dans un tableau pour pouvoir les utiliser pour les évènements (checkbox)
        markers_velib.push(marker);
      }
}

/**
 * Créer un tab d'objet qui contiennent les infos dont on a besoin
 * Pour cela il fait des appels aux api
 * @return tableau d'objets avec les infos des stations vélib
 */
async function creerTabVelib() {
    let url_stations_infos = "https://transport.data.gouv.fr/gbfs/nancy/station_information.json";
    let url_stations_status = "https://transport.data.gouv.fr/gbfs/nancy/station_status.json";

    // On fait un appel aux infos des stations
    let stations_infos = await loader.load_ressource(url_stations_infos);
    let tab_infos = stations_infos.data.stations;
    
    // On fait un appel aux status des stations
    let stations_status = await loader.load_ressource(url_stations_status);
    let tab_status = stations_status.data.stations;

    let mergedArray = [];

    // Boucle sur les objets du tableau tab_infos
    for (let i = 0; i < tab_infos.length; i++) {
        let mergedObj = {};

        // Recherche de l'objet correspondant dans le tableau tab_status
        let statusObj = tab_status.find((status) => status.station_id === tab_infos[i].station_id);

        // Fusion des propriétés des deux objets
        mergedObj = { ...tab_infos[i], ...statusObj };

        // Ajout de l'objet fusionné au nouveau tableau mergedArray
        mergedArray.push(mergedObj);
    }
    return mergedArray;
}

export default { creerTabVelib, displayOnMap, markers_velib };