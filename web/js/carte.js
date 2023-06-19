import velib from "./module/velib.js";
import traffic from "./module/traffic.js";
import restaurant from "./module/restaurant.js";
import sendFormulaire from "./sendFormulaire.js";

var map;

window.addEventListener("load", init);

async function init() {
    // On crée la map
    creerMap();

    // On crée le tableau des stations vélib et on les affiche sur la carte
    let stations_velib = await velib.creerTabVelib();
    velib.displayOnMap(map, stations_velib);

    // On crée le tableau des stations de traffic et on les affiche sur la carte
    let traffic_tab = await traffic.creerTabTraffic();
    traffic.displayOnMap(map, traffic_tab);

    // On crée le tableau des restaurants et on les affiche sur la carte
    let tab_restaurant = await restaurant.creerTabRestaurant();
    restaurant.displayOnMap(map, tab_restaurant);

    // On ajoute les évènements liés aux checkbox
    ajoutEvent();
}


/**
 * Créer la map et l'affiche dans la div "map"
 */
function creerMap() {
    map = L.map('map').setView([48.6880501, 6.19], 14);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
}

/**
 * Ajoute les évènements liés aux checkbox
 */
function ajoutEvent() {
    // Checkbox pour afficher ou cacher les markers
    let checkbox_velib = document.getElementById("checkbox-velib");
    checkbox_velib.addEventListener("change", function (e) {
        toggleMarker(e.target, velib.markers_velib);
    });

    let checkbox_traffic = document.getElementById("checkbox-traffic");
    checkbox_traffic.addEventListener("change", function (e) {
        toggleMarker(e.target, traffic.markers_traffic);
    });

    let checkbox_restaurant = document.getElementById("checkbox-restaurant");
    checkbox_restaurant.addEventListener("change", function (e) {
        toggleMarker(e.target, restaurant.markers_restaurant);
    });

    //Envoie du formulaire
    const form = document.querySelector('#formulaire');
    form.addEventListener('submit', async function(event) {
        event.preventDefault(); // Empêche la soumission du formulaire
        await sendFormulaire.envoyerFormulaire();
    });
}

/**
 * Permet de cacher ou afficher les markers d'une classe donnée
 * @param e qui correspond à l'élément html qui a déclenché l'évènement
 * @param classMarker qui correspond à la classe des markers à cacher ou afficher
 */
function toggleMarker(e, markers) {
    if (e.checked) {
        for (let marker of markers) {
            marker.setOpacity(1);
        }
    } else {
        for (let marker of markers) {
            marker.setOpacity(0);
        }
    }
}