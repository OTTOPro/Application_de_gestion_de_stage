package crosemont.dti.g55.application_gestion_de_stage.domaine
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.Source_bidon

enum class ÉtatCandiature {
    Accepté,
    Encours,
    Refusé
}

 class Candidature(
    val codeCandidature: Int,
    val offre: Offre,
    val etudiant: Etudiant ,
    val étatCandiature: ÉtatCandiature
) {
}

