package cn.sddman.bt.mvp.v;

import java.util.List;

import cn.sddman.bt.mvp.e.MagnetInfo;

public interface MagnetSearchView {
    void refreshData(List<MagnetInfo> info);
    void moreOption(MagnetInfo magnet);
}
