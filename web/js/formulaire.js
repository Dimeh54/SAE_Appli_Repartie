import loader from "./module/loader.js";


window.addEventListener("load", init);

let id_restaurant = null;

async function init() {
    let tab_restaurant = await getRestaurant();
    ajouterRestaurant(tab_restaurant);
    ajouterEvent();
}

/**
 * Ajoute tous les restaurants à la liste déroulante
 */
function ajouterRestaurant(tab_restaurant) {
    let dataList = document.getElementById("restaurantList");
    for (let restaurant of tab_restaurant) {
        let option = document.createElement("option");
        option.value = restaurant.nom;
        option.id = restaurant.id;
        dataList.appendChild(option);
    }
}

/**
 * Récupère le tableau des restaurants
 */
async function getRestaurant() {
    let url = "../Ressources/restaurants.json";
    let restaurants = await loader.load_ressource(url);
    return restaurants.restaurants;
}

function ajouterEvent() {
    // Récupérer l'élément de champ de recherche
    let searchInput = document.getElementById("searchInput");
    let dataList = document.getElementById("restaurantList");

    // Ajouter un écouteur d'événement pour la saisie de texte dans le champ de recherche
    searchInput.addEventListener("input", function() {
        let searchValue = searchInput.value.toLowerCase();

        // Parcourir les options de la liste déroulante
        let options = document.getElementById("restaurantList").options;
        for (let i = 0; i < options.length; i++) {
            let option = options[i];
            let optionText = option.value.toLowerCase();

            // Afficher ou masquer les options en fonction de la correspondance avec la recherche
            if (optionText.indexOf(searchValue) !== -1) {
                option.style.display = "";
            } else {
                option.style.display = "none";
            }
        }
    });

    // Ajouter un écouteur d'événement pour la sélection d'une option
    searchInput.addEventListener("change", function() {
        let selectedOptionId = null;
        let selectedOptionValue = searchInput.value.toLowerCase();

        // Parcourir les options pour trouver l'ID correspondant à la valeur sélectionnée
        let options = dataList.options;
        for (let i = 0; i < options.length; i++) {
            if (options[i].value.toLowerCase() === selectedOptionValue) {
                selectedOptionId = options[i].id;
                break;
            }
        }

        console.log("ID de l'option sélectionnée :", selectedOptionId);
        console.log("Valeur de l'option sélectionnée :", selectedOptionValue);

        // Si selectedOptionId est null, on indique dans que la valeur saisie n'est pas valide
        if (selectedOptionId === null) {
            searchInput.setCustomValidity("Nom de restaurant invalide");
        }
        else {
            searchInput.setCustomValidity("");
            id_restaurant = selectedOptionId;
        }
    });
}


