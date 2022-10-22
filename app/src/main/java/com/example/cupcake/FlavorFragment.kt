package com.example.cupcake

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.OrderViewModel

class FlavorFragment : Fragment() {

    private lateinit var runnable: Runnable
    private var handler = Handler()

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            viewModel = sharedViewModel

            flavorFragment = this@FlavorFragment

        }
        putarLagu()
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun putarLagu() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.music2)
        mediaPlayer.setScreenOnWhilePlaying(true)
        val tombol = binding!!.PlayButton
        tombol.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()

                tombol.setImageResource(R.drawable.ic_pause)
            } else {
                mediaPlayer.pause()
                tombol.setImageResource(R.drawable.ic_play)
            }
        }

        val seekbar = binding!!.SeekBar
        seekbar.progress = 0
        seekbar.max = mediaPlayer.duration
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mediaPlayer.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        runnable = Runnable {
            seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener {
            tombol.setImageResource(R.drawable.ic_play)
            seekbar.progress = 0
        }
        val tombolKembali: Button = binding!!.nextButton
        tombolKembali.setOnClickListener {
            goToNextScreen()
            mediaPlayer.stop()
        }
    }
}