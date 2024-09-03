package wangyeo.interview.qualgoo

import wangyeo.interview.qualgoo.bridges.flutter.FlutterChannelKind

data class ChannelFlutterViewState(
    val isStarted: Boolean,
    val arguments: String?,
    val kind: FlutterChannelKind = FlutterChannelKind.INTERNAL,
)