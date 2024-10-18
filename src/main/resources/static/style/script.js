document.addEventListener("DOMContentLoaded", function() {
    const freeCheckbox = document.getElementById('FREE'); // Make sure this ID matches your tag for 'Free'
    const priceField = document.getElementById('priceField');

    function togglePriceField() {
        if (freeCheckbox.checked) {
            priceField.style.display = 'none';
        } else {
            priceField.style.display = 'block';
        }
    }

    // Run the toggle function on page load
    togglePriceField();

    // Add event listener to checkbox
    freeCheckbox.addEventListener('change', togglePriceField);
});