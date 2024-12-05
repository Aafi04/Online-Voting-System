// script.js
// JavaScript logic for interactive elements or PWA features

// Example: Dark Mode toggle
document.addEventListener('DOMContentLoaded', (event) => {
    const toggleSwitch = document.getElementById('toggle-switch');
    const body = document.body;

    if (toggleSwitch) {
        toggleSwitch.addEventListener('change', () => {
            body.classList.toggle('dark-mode');
        });
    }
});