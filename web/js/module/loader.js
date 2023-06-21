/**
 * Requête vers une url donnée (json)
 * @return Objet JSON des données demandées
 */
async function load_ressource(url, fichierSecours = null) {
    try {
        let response = await fetch(url);
        if (response.ok) {
            let data = await response.json();
            return data;
        }
    } catch (err) {
        if (fichierSecours != null) {
            console.log("Erreur lors de la requête vers " + url + " : " + err);
            let response = await fetch(fichierSecours);
            if (response.ok) {
                let data = await response.json();
                return data;
            }
        }
        console.log(err);
    }
}

/**
 * Permet de charger le fichier de configuration
 */
async function loadConfig(callback) {
    var xhr = new XMLHttpRequest();
    xhr.overrideMimeType("application/json");
    xhr.open("GET", "../config.json", true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        var config = JSON.parse(xhr.responseText);
        callback(config);
      }
    };
    xhr.send(null);
  }


export default { load_ressource, loadConfig };