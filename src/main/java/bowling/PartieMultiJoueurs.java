package bowling;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {

	private String[] nomsDesJoueurs;
	private int joueurCourantIndex;
	private PartieMonoJoueur[] partiesJoueurs;
	private boolean partieTerminee;
	private int[] lancersEffectues;  // Table pour suivre le nombre de lancers de chaque joueur

	public PartieMultiJoueurs() {
		this.partieTerminee = false;
		this.lancersEffectues = new int[10]; // 10 pour un nombre maximum de joueurs dans la partie
	}
	//
	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) {
		if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("Le tableau des joueurs ne peut pas être vide");
		}

		this.nomsDesJoueurs = nomsDesJoueurs;
		this.joueurCourantIndex = 0;
		this.partiesJoueurs = new PartieMonoJoueur[nomsDesJoueurs.length];

		// Initialiser chaque joueur avec une partie mono-joueur
		for (int i = 0; i < nomsDesJoueurs.length; i++) {
			partiesJoueurs[i] = new PartieMonoJoueur();
		}

		// Initialiser le nombre de lancers pour chaque joueur
		for (int i = 0; i < nomsDesJoueurs.length; i++) {
			lancersEffectues[i] = 0;
		}

		return prochainTir(); // Retourne le message du prochain tir
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (partieTerminee) {
			return "Partie terminée";  // Si la partie est terminée, retourner un message de fin
		}

		// Enregistrement du lancer pour le joueur courant
		PartieMonoJoueur partieCourante = partiesJoueurs[joueurCourantIndex];
		partieCourante.enregistreLancer(nombreDeQuillesAbattues);  // Enregistre le lancer

		// Augmenter le compteur de lancers pour le joueur courant
		lancersEffectues[joueurCourantIndex]++;

		// Si le tour du joueur est terminé, on passe au joueur suivant
		String message;
		if (partieCourante.estTerminee()) {  // Vérifie si la partie du joueur est terminée
			joueurCourantIndex++;  // Passage au joueur suivant
			if (joueurCourantIndex == nomsDesJoueurs.length) {
				partieTerminee = true;  // La partie est terminée lorsque tous les joueurs ont joué
				message = "Partie terminée";  // Message de fin de partie
			} else {
				message = prochainTir();  // Préparer le prochain tir
			}
		} else {
			message = prochainTir();  // Retourner le message pour le prochain tir si le joueur n'a pas terminé
		}

		return message;  // Retourne le message du prochain tir ou la fin de la partie
	}

	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		// Cherche le joueur et retourne son score
		for (int i = 0; i < nomsDesJoueurs.length; i++) {
			if (nomsDesJoueurs[i].equals(nomDuJoueur)) {
				return partiesJoueurs[i].score();  // Retourne le score du joueur
			}
		}
		throw new IllegalArgumentException("Le joueur " + nomDuJoueur + " ne joue pas dans cette partie");
	}

	private String prochainTir() {
		// Calcul du numéro du tour et de la boule
		// Le tour est déterminé par les lancers effectués par le joueur
		int tour = lancersEffectues[joueurCourantIndex] / 2 + 1;  // Calcul du tour
		int boule = lancersEffectues[joueurCourantIndex] % 2 == 0 ? 1 : 2;  // Calcul de la boule
		return "Prochain tir : joueur " + nomsDesJoueurs[joueurCourantIndex] + ", tour n° " + tour + ", boule n° " + boule;
	}

	public boolean estTerminee() {
		return partieTerminee;  // Indique si la partie est terminée
	}
}
