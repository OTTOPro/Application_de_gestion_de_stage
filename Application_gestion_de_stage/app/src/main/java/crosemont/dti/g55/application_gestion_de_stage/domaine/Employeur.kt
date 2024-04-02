package crosemont.dti.g55.application_gestion_de_stage.domaine

class Employeur(

    val codeUtilisateur: String,
    val nomUtilisateur: String,
    val prénomUtilisateur: String,
    val courrielUtilisateur: String,
    val téléphoneUtilisateur: String,
    var codeEntreprise: Entreprise

) {
}