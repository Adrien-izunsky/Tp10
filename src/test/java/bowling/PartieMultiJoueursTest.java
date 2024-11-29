package bowling;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartieMultiJoueursTest {

	private PartieMultiJoueurs partie;
	private String[] joueurs;

	@BeforeEach
	public void setUp() {
		joueurs = new String[]{"Alice", "Bob", "Charlie"};
		partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(joueurs);
	}

	@Test
	public void testDemarreNouvellePartie() {
		// Vérifie que la partie est bien démarrée avec les joueurs
		assertNotNull(partie);
		assertEquals("Prochain tir : joueur Alice, tour n° 1, boule n° 1", partie.demarreNouvellePartie(joueurs));
	}
	//

	@Test
	public void testScoreInexistant() {
		// Test du cas où un joueur n'existe pas
		assertThrows(IllegalArgumentException.class, () -> {
			partie.scorePour("Inexistant");
		});
	}

	@Test
	public void testPartieTerminee() {
		// Vérifie que la partie est bien terminée
		for (int i = 0; i < 6; i++) {
			partie.enregistreLancer(10); // Chaque joueur fait un lancer
		}
		assertFalse(partie.estTerminee());
	}

	@Test
	public void testDemarrePartieAvecAucunJoueur() {
		// Vérifie qu'on ne peut pas démarrer une partie avec un tableau de joueurs vide
		assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(new String[]{});
		});
	}

	@Test
	public void testDemarrePartieAvecNull() {
		// Vérifie qu'on ne peut pas démarrer une partie avec null
		assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(null);
		});
	}
}
