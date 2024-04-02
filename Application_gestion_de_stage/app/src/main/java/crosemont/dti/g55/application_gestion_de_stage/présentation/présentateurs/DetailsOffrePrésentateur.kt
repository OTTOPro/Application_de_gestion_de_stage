package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.details_offre
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.IModèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsOffrePrésentateur(val vue: details_offre, modèle: IModèle = Modèle.instance ) {

    private val _modèle = modèle

    fun ajouterUneCandidature(code_offre: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _modèle.ajouterCandidature(code_offre)
        }
    }

}