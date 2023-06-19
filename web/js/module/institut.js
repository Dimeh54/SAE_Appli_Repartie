import loader from "./loader.js";

/**
 * Module correspondant à la gestion des instituts supérieurs
 */

var markers_institut = [];

/**
 * Affiche les markers des instituts supérieurs sur la carte
 * @param tableau avec les infos de chaque institut supérieur
 */
function displayOnMap(map, tab_institut) {
    // Créer une icône personnalisée
    var customIcon = L.icon({
        iconUrl: '../Ressources/institut.png',
        iconSize: [26, 26],
        iconAnchor: [0, 0],
        popupAnchor: [0, -40]
    });
    for (var i = 0; i < tab_institut.length; i++) {
        var marker = L.marker([tab_institut[i].latitude, tab_institut[i].longitude], { icon: customIcon }).addTo(map);

        // Popup du marker
        marker.bindPopup("<b>"+tab_institut[i].nom
            +"</b><p>"+tab_institut[i].adresse);

        // On récupère les markers dans un tableau pour pouvoir les utiliser pour les évènements (checkbox)
        markers_institut.push(marker);
      }
}

/**
 * Créer un tab d'objet qui contiennent les infos dont on a besoin
 * @return tableau d'objets avec les infos des instituts supérieurs
 */
async function creerTabInstitut() {
    //let url_institut = "https://proxy";
    let url_institut = "../Ressources/etablissements.json";

    // On fait un appel aux infos des instituts supérieurs
    let instituts = await loader.load_ressource(url_institut);
    //let tab_institut = instituts.instituts;
    let tab_institut = [];
    for (let institut of instituts) {
        let f = institut.fields;
        console.log(institut);
        
        // on vérifie si l'institut a une propriété type
        if (institut.geometry !== undefined) {
            tab_institut.push({
                "adresse":f.adresse_uai,
                "postal":f.code_postal_uai,
                "latitude":institut.geometry.coordinates[1],
                "longitude":institut.geometry.coordinates[0],
                "nom":f.uo_lib,
            });
        }
    }
    console.log(tab_institut);
    return tab_institut;
}

export default { creerTabInstitut, displayOnMap, markers_institut };