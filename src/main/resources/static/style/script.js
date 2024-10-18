document.addEventListener("DOMContentLoaded", function() {
    const freeCheckbox = document.getElementById('FREE'); // Make sure this ID matches your tag for 'Free'
    const priceField = document.getElementById('priceField');
    const setPriceToZero = document.getElementById('price')


    function togglePriceField() {
        console.log(setPriceToZero)

        if (freeCheckbox && freeCheckbox.checked) {
            priceField.style.display = 'none';
            setPriceToZero.value = "0.0";

        } else {
            priceField.style.display = 'block';

        }
    }

    // Run the toggle function on page load
    togglePriceField();

    // Add event listener to checkbox
    freeCheckbox.addEventListener('change', togglePriceField);

    if (freeCheckbox) {
        freeCheckbox.addEventListener('change', togglePriceField);
    }
});