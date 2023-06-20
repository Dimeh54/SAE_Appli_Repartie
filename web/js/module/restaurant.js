import loader from "./loader.js";

/**
 * Module correspondant à la gestion des restaurants
 */

var markers_restaurant = [];


/**
 * Affiche les markers des restaurants sur la carte
 * @param tableau avec les infos de chaque restaurant
 */
function displayOnMap(map, tab_restaurant) {
    // Créer une icône personnalisée
    var customIcon = L.icon({
        iconUrl: '../Ressources/resto.png',
        iconSize: [26, 26],
        iconAnchor: [0, 0],
        popupAnchor: [0, -40]
    });
    for (var i = 0; i < tab_restaurant.length; i++) {
        var marker = L.marker([tab_restaurant[i].latitude, tab_restaurant[i].longitude], { icon: customIcon }).addTo(map);

        // Popup du marker
        marker.bindPopup("<b>"+tab_restaurant[i].nom
            +"</b><p>"+tab_restaurant[i].adresse
            +"</p><button class='w3-small w3-padding-small' onclick='openCard(\""+ tab_restaurant[i].nom +"\","+ tab_restaurant[i].id +")'>Réserver maintenant</button>");

        // On récupère les markers dans un tableau pour pouvoir les utiliser pour les évènements (checkbox)
        markers_restaurant.push(marker);
      }
    let layerRestaurant = L.layerGroup(markers_restaurant);
    layerRestaurant.addTo(map);
    return layerRestaurant;
}

/**
 * Créer un tab d'objet qui contiennent les infos dont on a besoin
 * @return tableau d'objets avec les infos des restaurants
 */
async function creerTabRestaurant() {
    // On fait un appel aux infos des restaurants
    //let url = "../Ressources/restaurants.json";
    let url = "https://127.0.0.1:8000/api/restaurants";
    let restaurants = await loader.load_ressource(url);
    let tab_restaurant = restaurants.restaurants;

    return tab_restaurant;
}

export default { creerTabRestaurant, displayOnMap, markers_restaurant };