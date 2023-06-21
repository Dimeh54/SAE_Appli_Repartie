import velib from "./module/velib.js";
import traffic from "./module/traffic.js";
import restaurant from "./module/restaurant.js";
import institut from "./module/institut.js";
import loader from "./module/loader.js";
import sendFormulaire from "./sendFormulaire.js";

var map;
var adresse;

window.addEventListener("load", init);

async function init() {
    // On crée la map
    creerMap();

    adresse = await loader.loadConfig(function (config) {
        adresse = config.adresse;
    });

    // On crée le tableau des stations vélib et on les affiche sur la carte
    let stations_velib = await velib.creerTabVelib();
    let layerVelib = velib.displayOnMap(map, stations_velib);

    // On crée le tableau des stations de traffic et on les affiche sur la carte
    let traffic_tab = await traffic.creerTabTraffic();
    let layerTraffic = traffic.displayOnMap(map, traffic_tab);

    // On crée le tableau des restaurants et on les affiche sur la carte
    let tab_restaurant = await restaurant.creerTabRestaurant(adresse);
    let layerRestaurant = restaurant.displayOnMap(map, tab_restaurant);

    // On crée le tableau des instituts et on les affiche sur la carte
    let tab_institut = await institut.creerTabInstitut(adresse);
    let layerInstitut = institut.displayOnMap(map, tab_institut);
    
    let overlayMaps = {
        "Vélibs": layerVelib,
        "Restaurants": layerRestaurant,
        "Instituts": layerInstitut,
        "Trafic": layerTraffic
    };

    let control = L.control.layers(null, overlayMaps).addTo(map);


    // Ajout des évenements
    ajoutEvent();
    
    //testMarkerRestaurant();
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
            marker.setZIndexOffset(0);
            marker.setOpacity(1);
        }
    } else {
        for (let marker of markers) {
            marker.setZIndexOffset(-10);
            marker.setOpacity(0);
        }
    }
}