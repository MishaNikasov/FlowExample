package com.app.flowexample.presentation.fragment.main

import com.app.flowexample.databinding.FragmentSecondBinding
import com.app.flowexample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate)