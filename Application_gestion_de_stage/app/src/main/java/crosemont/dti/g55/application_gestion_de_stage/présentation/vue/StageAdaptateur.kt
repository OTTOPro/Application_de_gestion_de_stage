package crosemont.dti.g55.application_gestion_de_stage.présentation.vue

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import crosemont.dti.g55.application_gestion_de_stage.R
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.domaine.StageProgression

class StageAdaptateur(private var stages: List<Stage>):
    RecyclerView.Adapter<StageAdaptateur.StageViewHolder>() {

    inner class StageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titreDuPosteEtNomEntreprise: TextView = itemView.findViewById(R.id.titre_du_poste_et_nom_entreprise)
        val dateDebutEtFinStage: TextView = itemView.findViewById(R.id.date_début_et_fin)
        val nomPrenomSuperviseur: TextView = itemView.findViewById(R.id.nom_prenom_superviseur)
        val adresseStage: TextView = itemView.findViewById(R.id.adresse_stage)
        val cocheDebuté : ImageView = itemView.findViewById(R.id.coche_débuté)
        val cocheEncours : ImageView = itemView.findViewById(R.id.coche_encours)
        val cocheTerminé : ImageView = itemView.findViewById(R.id.coche_terminé)
        val contacterBouton: Button = itemView.findViewById(R.id.contacter_button)
        val pasDeStage: TextView = itemView.findViewById(R.id.pas_de_stage_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StageAdaptateur.StageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stage, parent, false)
        return StageViewHolder(view)    }

    override fun onBindViewHolder(holder: StageViewHolder, position: Int) {

        val stage = stages[position]

        if (stage != null) {
            holder.titreDuPosteEtNomEntreprise.text = stage.candidatureAcceptée?.offre?.titrePoste + " - " + stage.employeur.codeEntreprise.nomEntreprise
            holder.dateDebutEtFinStage.text = stage.dateDébut + " " + stage.dateFin
            holder.nomPrenomSuperviseur.text = stage.superviseurAssigné
            holder.adresseStage.text = stage.lieu

            when (stage.stageProgression) {
                StageProgression.DÉBUTÉ -> holder.cocheDebuté.visibility = View.VISIBLE
                StageProgression.ENCOURS -> holder.cocheEncours.visibility = View.VISIBLE
                StageProgression.TERMINÉ -> holder.cocheTerminé.visibility = View.VISIBLE
            }

            holder.contacterBouton.setOnClickListener{

                val superviseurCourriel = stage.superviseurAssigné.replace(" ", "")

                val courrielIntent = Intent(Intent.ACTION_SEND)

                courrielIntent.type = "text/plain"
                courrielIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("${superviseurCourriel}@crosemont.qc.ca"))
                courrielIntent.putExtra(Intent.EXTRA_SUBJECT, "Rendez-Vous")
                courrielIntent.putExtra(Intent.EXTRA_TEXT, "Bonjour,\n\n Êtes-vous disponible pour un rendez-vous ?\n\n Merci, \n\n ${stage.stagiaire!!.prénomUtilisateur} ${stage.stagiaire!!.nomUtilisateur}")


                holder.itemView.context.startActivity(Intent.createChooser(courrielIntent, "Envoyer courriel..."))
            }

        } else {

            holder.pasDeStage.text = "Vous n'avez pas encore un stage"
            holder.pasDeStage.visibility = View.VISIBLE
            holder.titreDuPosteEtNomEntreprise.visibility = View.GONE
            holder.dateDebutEtFinStage.visibility = View.GONE
            holder.nomPrenomSuperviseur.visibility = View.GONE
            holder.adresseStage.visibility = View.GONE
            holder.cocheDebuté.visibility = View.GONE
            holder.cocheEncours.visibility = View.GONE
            holder.cocheTerminé.visibility = View.GONE
            holder.contacterBouton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return stages.size
    }

    fun setStage(stages: List<Stage>) {
        this.stages = stages
        notifyDataSetChanged()
    }

}