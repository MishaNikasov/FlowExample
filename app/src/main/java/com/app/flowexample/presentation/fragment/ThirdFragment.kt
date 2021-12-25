package com.app.flowexample.presentation.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.app.flowexample.common.extensions.collectWhenStarted
import com.app.flowexample.databinding.FragmentSecondBinding
import com.app.flowexample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ThirdFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate)