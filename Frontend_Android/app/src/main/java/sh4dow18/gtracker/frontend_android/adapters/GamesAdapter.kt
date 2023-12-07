package sh4dow18.gtracker.frontend_android.adapters

import android.annotation.SuppressLint
import sh4dow18.gtracker.frontend_android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.databinding.GameCardItemBinding
import sh4dow18.gtracker.frontend_android.utils.GameResponse

class GamesAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var gamesList = mutableListOf<GameResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGamesList(newGamesList: List<GameResponse>){
        this.gamesList.clear()
        this.gamesList.addAll(newGamesList.toMutableList())
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GameCardItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val game = gamesList[position]
        if (game.imageUrl != "") {
            Glide.with(holder.itemView.context)
                .load(game.imageUrl)
                .centerCrop()
                .into(holder.binding.GameImage)
        }
        val gameMetacritic = "Rating(Metacritic): " + game.metacritic + " / 100"
        val gameRating = "Rating(Users): " + game.rating + " / 5"
        holder.binding.GameName.text = game.name
        holder.binding.Metacritic.text = gameMetacritic
        holder.binding.Genders.text = gameRating
        holder.binding.GameItemContainer.setOnClickListener {
            val bundle = bundleOf("game_id" to gamesList[position].id)
            holder.itemView.findNavController().navigate(R.id.nav_game, bundle)
        }
    }
}

class MainViewHolder(
    val binding : GameCardItemBinding
): RecyclerView.ViewHolder(binding.root)