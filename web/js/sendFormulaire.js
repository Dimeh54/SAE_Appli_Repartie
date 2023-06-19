window.onload = function() {
    let id_restaurant = null;
    envoyerFormulaire(id_restaurant);
}


/**
 * Permet de réserver un restaurant
 * Envoie une requête POST au serveur proxy
 * @param {*} informations de la réservation
 */
async function reserver(informations) {
    let url = "https://localhost:8000/api/reservation";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(informations),
        });

        if (response.ok) {
            let data = await response.json();
            console.log("Success: ", data);
            return data;
        }
    } catch (err) {
        console.log("Error: ",err);
    }
}

/**
 * Pertmet d'envoyer le formulaire de réservation
 * @param idRestaurant 
 */
async function envoyerFormulaire(idRestaurant) {
    const loadingIcon = document.querySelector('#loading-icon');
    const form = document.querySelector('#formulaire');

    loadingIcon.style.display = 'block';

    // Récupérer les valeurs des champs du formulaire
    const formData = new FormData(form);
    const informations = {
        nom: formData.get('nom'),
        prenom: formData.get('prenom'),
        telephone: formData.get('phone'),
        personnes: formData.get('personnes'),
        date: formData.get('date'),
        heure: formData.get('heure'),
        restaurant: formData.get('restaurant'),
        id: idRestaurant
    };

    // Envoyer les informations au serveur
    let response = await reserver(informations);
    
    // Afficher un message de confirmation
    loadingIcon.style.display = 'none';
    let message = document.querySelector('#response');
    if (response) {
        message.innerHTML = "Votre réservation a bien été prise en compte !";
        message.style.display = 'block';
    }
    else {
        message.innerHTML = "Une erreur est survenue lors de la réservation.";
        message.style.display = 'block';
    }

    console.log("Formulaire soumis");
}

export default { envoyerFormulaire };