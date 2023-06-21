/**
 * Permet de réserver un restaurant
 * Envoie une requête POST au serveur proxy
 * @param {*} informations de la réservation
 */
async function reserver(informations, adresse = "http://localhost:8000") {
    let url = adresse + "/api/reservation";
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
async function envoyerFormulaire(idRestaurant = null) {
    const loadingIcon = document.querySelector('#loading-icon');
    const form = document.querySelector('#formulaire');

    loadingIcon.style.display = 'block';

    // Récupérer les valeurs des champs du formulaire
    const formData = new FormData(form);

    if (idRestaurant === null) {
        idRestaurant = formData.get('id');
    }
    const informations = {
        nom: formData.get('nom'),
        prenom: formData.get('prenom'),
        numtel: formData.get('phone'),
        nbpers: formData.get('personnes'),
        date : (formData.get('date') + " " + formData.get('heure')),
        id_restaurant: idRestaurant
    };
    console.log(informations);

    // Envoyer les informations au serveur
    let response = await reserver(informations, adresse);
    
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