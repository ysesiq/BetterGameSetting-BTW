# Better Game Setting for BTW

### v1.3.0
* 加入了输入法冲突修复（ https://github.com/lss233/InputMethodBlocker ）
* 修复了FontFixer无法正常启用的问题
* 修复了全角左括号“（”不显示的问题

---

### v1.2.0
* 加入了1.7.2+的视频设置界面
    * 考虑到兼容性（光影模组），不添加mipmap与各项异性过滤选项
* 合并了FontFixer模组
* 优化了控制界面的滚动条
* 优化了代码
* 修复了与FreeLook模组间的兼容问题
* 修复了与部分模组同时加载时，模组的GuiSlot没有深色背景的问题
* 修复了点击本模组新增GuiSlot时，不播放音效的问题
* 修复了非Mojangles（ASCII）字体不显示文本阴影的问题

---

## 1.1.0
* 移植自BetterGameSetting（MITE）
* 因为Mixin Extra与EnumExtends缺失，暂时不移植声音设置方面内容
* 可以选择多个资源包
* 更好的调整渲染距离和Gui缩放
* 自定义Fps最大值
* 更低的Fov
* 更好的控制设置（注意：目前不支持重置mod的按键绑定）