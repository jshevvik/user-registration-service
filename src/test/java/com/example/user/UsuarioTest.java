package com.example.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    //para no repetir el codigo
    private Usuario base(){
        return new Usuario("1", "a@test.com", "H");
    }
    private static String n(String s){
        //"NULL" -> null
        return "NULL".equals(s)? null : s;
    }


//      Un objeto debe ser igual a sí mismo

    @Test
    void equals_esReflexiva() {
        Usuario u = new Usuario("1", "a@b.com", "H");
        assertEquals(u, u); // this == o → true
    }


//     Comparación contra null y contra objeto de otro tipo

    @Test
    void equals_contraNull_y_tipoDistinto() {
        Usuario u = base();
        assertNotEquals(u, null);    // comparar con null → false
        assertNotEquals(u, "texto"); // comparar con otro tipo → false
    }


//     Todos los campos iguales

    @Test
    void equals_todosCamposIguales_true_y_hashConsistente() {
        Usuario u1 = base();
        Usuario u2 = base();
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

//    /**
//     * Caso 4 — Id distinto
//     * Cambiar el id debe hacer que los objetos no sean iguales.
//     */
//    @Test
//    void equals_idDistinto_false() {
//        Usuario u1 = new Usuario("1", "a@test.com", "H");
//        Usuario u2 = new Usuario("2", "a@test.com", "H");
//        assertNotEquals(u1, u2);
//    }
//
//    /**
//     * Caso 5 — Email distinto
//     * Cambiar el email debe romper la igualdad.
//     */
//    @Test
//    void equals_emailDistinto_false() {
//        Usuario u1 = new Usuario("1", "a@test.com", "H");
//        Usuario u2 = new Usuario("1", "c@com.com", "H");
//        assertNotEquals(u1, u2);
//    }
//
//    /**
//     * Caso 6 — Hash de contraseña distinto
//     * Cambiar el passwordHash debe romper la igualdad.
//     */
//    @Test
//    void equals_passwordHashDistinta_false() {
//        Usuario u1 = new Usuario("1", "a@test.com", "H1");
//        Usuario u2 = new Usuario("1", "a@test.com", "H2");
//        assertNotEquals(u1, u2);
//    }

    // PARAMETRIZADO: “cambia un campo” → no iguales
    @ParameterizedTest(name = "Distinto → id={0}, email={1}, hash={2}")
    @CsvSource({
            // id distinto
            "2, a@test.com, H",
            // email distinto
            "1, c@com.com, H",
            // passwordHash distinto
            "1, a@test.com, H2"
    })
    void equals_cambiaUnCampo_noSonIguales(String id, String email, String hash) {
        Usuario u1 = base();
        Usuario u2 = new Usuario(id, email, hash);
        assertNotEquals(u1, u2);
    }



//    Todos los campos null en ambos objetos

    @Test
    void equals_camposNull_enAmbos_true() {
        Usuario u1 = new Usuario(null, null, null);
        Usuario u2 = new Usuario(null, null, null);
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }


    // PARAMETRIZADO: “null vs valor” por campo
    @ParameterizedTest(name = "Null vs Valor → id={0}, email={1}, hash={2}")
    @CsvSource({
            // id: null vs valor
            "NULL, a@test.com, H",
            // email: null vs valor
            "1, NULL, H",
            // passwordHash: null vs valor
            "1, a@test.com, NULL"
    })
    void equals_null_vs_valor_noSonIguales(String id, String email, String hash) {
        // uNull tiene el campo marcado como "NULL" realmente a null
        Usuario uNull = new Usuario(n(id), n(email), n(hash));
        // uVal es el mismo usuario base con valores reales
        Usuario uVal  = base();
        assertNotEquals(uNull, uVal);
    }

     //Simetría: si a.equals(b) es true, b.equals(a) también debe ser true.
    @Test
    void equals_esSimetrica(){
        Usuario u1 =base();
        Usuario u2 = base();
        assertTrue(u1.equals(u2) && u2.equals(u1));
    }

     //Transitividad: si a.equals(b) y b.equals(c), entonces a.equals(c).

    @Test
    void equals_esTransitiva(){
        Usuario u1 = base();
        Usuario u2 = base();
        Usuario u3 = base();
        assertTrue(u1.equals(u2) && u2.equals(u3) && u1.equals(u3));
    }

     //Consistencia: repetir equals varias veces sin cambios y obtener el mismo resultado.
    @Test
    void equals_esConsistente(){
        Usuario u1 = base();
        Usuario u2 = base();
        for (int i = 0; i < 1000; i++){
            assertEquals(u1,u2);
            assertEquals(u1.hashCode(), u2.hashCode());
        }
    }
     //Compatibilidad con colecciones: comprobar comportamiento en HashSet/HashMap.
    @Test
    void comportamiento_enHashSet(){
        Usuario u1 = base();
        Usuario u2 = base();
        Set<Usuario> set = new HashSet<>();
        set.add(u1);
        set.add(u2);
        assertEquals(1, set.size());
    }
}
