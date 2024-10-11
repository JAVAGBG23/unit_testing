# Identifierade negativa scenarier

## skapa produkt:
Skapa en produkt där obligatoriska fält saknas (t.ex. namn, pris).

Skapa en produkt med ogiltiga data (t.ex. negativt pris, för stort lagerkvantitet).

## getAllProducts:
Hantera fall där repository returnerar en tom lista.

## getProductsByName:
Hämta produkter med ett namn som inte finns.
Skickar en null eller tom sträng som namn.

## getProductsByPriceRange:
Ogiltiga prisintervall (t.ex. minPrice större än maxPrice).
Negativa prisvärden.

## getProductsByColor:
Hämta produkter med en färg som inte finns.
Skicka en null eller tom sträng som färg.