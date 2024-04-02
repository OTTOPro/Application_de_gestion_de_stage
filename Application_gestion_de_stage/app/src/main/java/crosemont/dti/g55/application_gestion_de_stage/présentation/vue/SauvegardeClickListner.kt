package crosemont.dti.g55.application_gestion_de_stage.présentation.vue

import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre

interface SauvegardeClickListner {
    fun onSauvegarderClick(offre: Offre)
}