package pl.edu.agh.kis.pz1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Guest class.
 * Verifies the correct behavior of constructors, getters, setters,
 * and overridden equals and hashCode methods.
 */
class GuestTest {

    @Test
    void testConstructorAndGetters() {
        Guest guest = new Guest("John", "Doe", true);

        assertEquals("John", guest.getFirstName(), "First name should be 'John'");
        assertEquals("Doe", guest.getLastName(), "Last name should be 'Doe'");
        assertTrue(guest.isMainGuest(), "Guest should be marked as main guest");
    }

    @Test
    void testSetters() {
        Guest guest = new Guest("John", "Doe", true);

        guest.setFirstName("Jane");
        guest.setLastName("Smith");
        guest.setMainGuest(false);

        assertEquals("Jane", guest.getFirstName(), "First name should be updated to 'Jane'");
        assertEquals("Smith", guest.getLastName(), "Last name should be updated to 'Smith'");
        assertFalse(guest.isMainGuest(), "Guest should not be marked as main guest");
    }

    @Test
    void testEqualsSameObject() {
        Guest guest = new Guest("John", "Doe", true);
        assertEquals(guest, guest, "Guest should be equal to itself");
    }

    @Test
    void testEqualsEqualObjects() {
        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("John", "Doe", true);

        assertEquals(guest1, guest2, "Guests with same data should be equal");
        assertEquals(guest1.hashCode(), guest2.hashCode(), "Hash codes should be equal for equal guests");
    }

    @Test
    void testEqualsDifferentFirstName() {
        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("Jane", "Doe", true);

        assertNotEquals(guest1, guest2, "Guests with different first names should not be equal");
    }

    @Test
    void testEqualsDifferentLastName() {
        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("John", "Smith", true);

        assertNotEquals(guest1, guest2, "Guests with different last names should not be equal");
    }

    @Test
    void testEqualsDifferentMainGuestStatus() {
        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("John", "Doe", false);

        assertNotEquals(guest1, guest2, "Guests with different main guest status should not be equal");
    }

    @Test
    void testEqualsNull() {
        Guest guest = new Guest("John", "Doe", true);
        assertNotEquals(null, guest, "Guest should not be equal to null");
    }

    @Test
    void testEqualsDifferentClass() {
        Guest guest = new Guest("John", "Doe", true);
        Object obj = new Object();

        assertNotEquals(guest, obj, "Guest should not be equal to an object of a different class");
    }

    @Test
    void testHashCodeConsistency() {
        Guest guest = new Guest("John", "Doe", true);
        int initialHashCode = guest.hashCode();

        assertEquals(initialHashCode, guest.hashCode(), "Hash code should be consistent across multiple calls");

        guest.setFirstName("Jane");
        assertNotEquals(initialHashCode, guest.hashCode(), "Hash code should change when state changes");
    }

}
