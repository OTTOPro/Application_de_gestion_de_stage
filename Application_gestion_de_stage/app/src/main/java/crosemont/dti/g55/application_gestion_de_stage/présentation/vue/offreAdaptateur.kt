package crosemont.dti.g55.application_gestion_de_stage.présentation.vue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import crosemont.dti.g55.application_gestion_de_stage.R
import crosemont.dti.g55.application_gestion_de_stage.details_offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre

class offreAdaptateur(private var offres: List<Offre>, private val candidatureClickListener: CandidatureClickListener): RecyclerView.Adapter<offreAdaptateur.OffreViewHolder>() {


    inner class OffreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val frameLayout: FrameLayout = itemView.findViewById(R.id.frameLayout)
        val postulerButton: Button = itemView.findViewById(R.id.postulerButton)
        val favorisImageButton: ImageButton = itemView.findViewById(R.id.btn_favoris)
        val entrepriseDuPosteTextView: TextView = itemView.findViewById(R.id.entreprise_offre)
        val lieuTextView: TextView = itemView.findViewById(R.id.lieu_offre)
        val typeTextView: TextView = itemView.findViewById(R.id.type_offre)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_offre)

        fun bind(offre: Offre) {
            if (offre.offreSauvegardée) {
                favorisImageButton.setImageResource(R.drawable.favori)
            } else {
                favorisImageButton.setImageResource(R.drawable.favori_en_noir)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offres, parent, false)
        return OffreViewHolder(view)
    }

    override fun onBindViewHolder(holder: OffreViewHolder, position: Int) {
        val offre = offres[position]


        holder.entrepriseDuPosteTextView.text = offre.titrePoste + " - " + offre.employeur.codeEntreprise.nomEntreprise
        holder.lieuTextView.text = offre.employeur.codeEntreprise.adresseEntreprise
        holder.typeTextView.text = offre.modeEmploi.name
        holder.descriptionTextView.text = offre.description

        holder.postulerButton.setOnClickListener {
            details_offre.offre = offre
            candidatureClickListener.onPostulerClick()
        }
        holder.frameLayout.setBackgroundResource(R.drawable.btn_televerser)
    }

    override fun getItemCount(): Int {
        return offres.size
    }

    fun setOffres(offres: List<Offre>) {
        this.offres = offres
        notifyDataSetChanged()
    }

}