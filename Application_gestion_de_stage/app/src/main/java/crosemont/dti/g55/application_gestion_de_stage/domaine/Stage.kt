package crosemont.dti.g55.application_gestion_de_stage.domaine

class Stage (
    val idStage: Int,
    val stagiaire: Etudiant?,
    val candidatureAcceptée: Candidature?,
    val superviseurAssigné: String,
    val employeur: Employeur,
    val stageProgression: StageProgression,
    val lieu: String,
    val dateDébut: String,
    val dateFin: String
) {
}
enum class StageProgression {
    DÉBUTÉ,
    ENCOURS,
    TERMINÉ
}
