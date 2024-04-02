package crosemont.dti.g55.application_gestion_de_stage.domaine

import java.io.Serializable

class Offre(
    var idOffre: Int,
    var employeur: Employeur,
    val titrePoste: String,
    val modeEmploi: ModeEmploi,
    val description: String,
    var offreSauvegardée: Boolean = false,
    var candidatures: List<Candidature> = mutableListOf()

) : Serializable {


}