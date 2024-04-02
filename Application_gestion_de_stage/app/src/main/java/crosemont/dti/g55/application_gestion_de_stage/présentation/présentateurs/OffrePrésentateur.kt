package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import OffresSauvegardées_modèle
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.ecran_recherche
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.IModèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonnéesException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OffrePrésentateur(val vue: ecran_recherche,modèle: IModèle = Modèle.instance ) {

    private val _modèle = modèle
    private var offres: List<Offre> = emptyList()

    fun chargerToutesLesOffres() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    vue.afficherEcranChargement(true)
                }

                delay(3000)
                offres = _modèle.obtenirToutesLesOffres()

                withContext(Dispatchers.Main) {
                    vue.afficherOffres(offres)
                    vue.afficherEcranChargement(false)
                }
            } catch (e: SourceDeDonnéesException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreur(e.message ?: "Erreur inconnue")
                    vue.afficherEcranChargement(false)
                }
            }
        }
    }

    fun filtrerOffres(mot: String) {
        CoroutineScope( Dispatchers.IO ).launch {
            val offresFiltrees = offres.filter { offre ->
                offre.titrePoste.contains(mot, true) || offre.employeur.codeEntreprise.nomEntreprise.contains(mot, true)
            }
            CoroutineScope( Dispatchers.Main ).launch {
                vue.afficherOffres(offresFiltrees)
            }
        }
    }

}