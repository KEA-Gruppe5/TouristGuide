document.addEventListener("DOMContentLoaded", function() {
    const freeCheckbox = document.getElementById('freeCheckbox');
    const priceField = document.getElementById('priceField');

    function togglePriceField() {
        if (!freeCheckbox.checked) {
            priceField.style.display = 'block';
        } else {
            priceField.style.display = 'none';
        }
    }

    // Run the toggle function on page load in case the checkbox is pre-checked
    togglePriceField()

    // Add event listener to checkbox
    freeCheckbox.addEventListener('change', togglePriceField);
});