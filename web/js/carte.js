import velib from "./module/velib.js";
import traffic from "./module/traffic.js";

var map;

window.addEventListener("load", init);

async function init() {
    // On crée la map
    creerMap();

    // On crée le tableau des stations vélib
    let stations_velib = await velib.creerTabVelib();
    // On affiche les markers des stations vélib sur la carte
    velib.displayOnMap(map, stations_velib);

    let traffic_tab = await traffic.creerTabTraffic();
    traffic.displayOnMap(map, traffic_tab);

    // On ajoute les évènements liés aux checkbox
    ajoutEvent();
    
    testMarkerRestaurant();
}

function testMarkerRestaurant() {
    var marker = L.marker([48.65434898630919,6.105573995362762]).addTo(map);

        // Popup du marker
        marker.bindPopup("<button class='w3-button w3-blue' onclick='openCard()'>Réservation</button>");
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
    let checkbox_velib = document.getElementById("checkbox-velib");
    checkbox_velib.addEventListener("change", function (e) {
        toggleMarker(e.target, velib.markers_velib);
    });

    let checkbox_traffic = document.getElementById("checkbox-traffic");
    checkbox_traffic.addEventListener("change", function (e) {
        toggleMarker(e.target, traffic.markers_traffic);
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