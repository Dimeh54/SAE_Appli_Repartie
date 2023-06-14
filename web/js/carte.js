var map = L.map('map').setView([48.6880501, 6.19], 14);
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);
var marker = L.marker([48.6864306, 6.1697993]).addTo(map);
marker.bindPopup("<b>Les P'tits Oignons</b><br>48 Rue Jeanne d'Arc");