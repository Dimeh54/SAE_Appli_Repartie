/**
 * Requête vers une url donnée (json)
 * @return Objet JSON des données demandées
 */
async function load_ressource(url) {
    try {
        let response = await fetch(url);
        if (response.ok) {
            let data = await response.json();
            return data;
        }
    } catch (err) {
        console.log(err);
    }
}

export default { load_ressource };