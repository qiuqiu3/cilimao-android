package cn.sddman.bt.listener;

import java.util.List;

import cn.sddman.bt.mvp.e.MagnetInfo;

public interface MagnetSearchListener {
    void success(List<MagnetInfo> info);
    void fail(String error);
}
