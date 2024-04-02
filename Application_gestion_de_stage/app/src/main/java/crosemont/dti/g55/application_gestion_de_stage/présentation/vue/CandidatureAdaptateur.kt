package crosemont.dti.g55.application_gestion_de_stage.présentation.vue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import crosemont.dti.g55.application_gestion_de_stage.R
import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.ÉtatCandiature

class CandidatureAdaptateur(private var candidatures: List<Candidature>, private var suppressionClickListener: SuppressionClickListener) : RecyclerView.Adapter<CandidatureAdaptateur.CandiatureViewHolder>() {

    inner class CandiatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val candidature_entreprise: TextView = itemView.findViewById(R.id.entreprise_Candidature)
        val candidature_lieu: TextView = itemView.findViewById(R.id.lieu_candidature)
        val candidature_type: TextView = itemView.findViewById(R.id.type_candidature)
        val candidature_description: TextView = itemView.findViewById(R.id.description_candidature)
        val candidature_etat: TextView = itemView.findViewById(R.id.etatCandidature)
        val btn_supprimer: ImageButton = itemView.findViewById(R.id.btn_supprimer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandiatureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_candidatures, parent, false)
        return CandiatureViewHolder(view)
    }

    override fun getItemCount(): Int {
       return candidatures.size
    }

    override fun onBindViewHolder(holder: CandiatureViewHolder, position: Int) {
        val candidature = candidatures[position]

        holder.candidature_entreprise.text = candidature.offre.titrePoste + " - " + candidature.offre.employeur.codeEntreprise.nomEntreprise
        holder.candidature_lieu.text = candidature.offre.employeur.codeEntreprise.adresseEntreprise
        holder.candidature_type.text = candidature.offre.modeEmploi.name
        holder.candidature_description.text = candidature.offre.description
        if (candidature.étatCandiature == ÉtatCandiature.Encours){
            holder.candidature_etat.text = "En cours"
        }
        holder.btn_supprimer.setOnClickListener {
            suppressionClickListener.onSuppressionClick(candidature.codeCandidature)
        }
    }

    fun setCandidatures(candidatures: List<Candidature>) {
        this.candidatures = candidatures
        notifyDataSetChanged()
    }
}