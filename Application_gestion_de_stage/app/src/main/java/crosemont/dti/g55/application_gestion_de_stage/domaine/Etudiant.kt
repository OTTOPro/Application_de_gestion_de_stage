package crosemont.dti.g55.application_gestion_de_stage.domaine

class Etudiant(
     val codeUtilisateur: String,
     val nomUtilisateur: String,
     val prénomUtilisateur: String,
     val courrielUtilisateur: String,
     val téléphoneUtilisateur: String,
    val profilInformatique: ProfilInformatique,
    val stageIntégration: Boolean,
    val adresseÉtudiant: String
) {
}