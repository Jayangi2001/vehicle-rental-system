const KEY = "myRentals";

export function saveLocalRental(rental) {
  const existing = getLocalRentals();
  existing.unshift(rental);
  localStorage.setItem(KEY, JSON.stringify(existing));
}

export function updateLocalRentalStatus(rentalId, paid) {
  const existing = getLocalRentals().map((r) =>
    r.id === rentalId ? { ...r, paid } : r
  );
  localStorage.setItem(KEY, JSON.stringify(existing));
}

export function getLocalRentals() {
  try {
    return JSON.parse(localStorage.getItem(KEY)) || [];
  } catch {
    return [];
  }
}