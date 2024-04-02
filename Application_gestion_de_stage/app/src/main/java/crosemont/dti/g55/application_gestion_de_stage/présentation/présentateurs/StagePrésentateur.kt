package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.ecran_stage
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.IModèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonnéesException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StagePrésentateur(val vue: ecran_stage, modèle:IModèle = Modèle.instance) {

    private val _modèle = modèle

    fun chargerStage(){
        CoroutineScope( Dispatchers.IO ).launch {
            try {
                withContext(Dispatchers.Main) {
                    vue.afficherEcranChargement(true)
                }
                delay(3000)
                val stage = _modèle.obtenirStage()

                withContext(Dispatchers.Main) {
                    vue.afficherStage(stage)
                    vue.afficherEcranChargement(false)
                }
            } catch (e: SourceDeDonnéesException) {
                withContext( Dispatchers.Main ) {
                    vue.afficherErreur(e.message ?: "Erreur inconnue")
                    vue.afficherEcranChargement(false)

                }
            }
        }
    }
}