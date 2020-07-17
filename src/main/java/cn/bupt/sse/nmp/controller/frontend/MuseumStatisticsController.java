package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.entity.LocInfo;
import cn.bupt.sse.nmp.result.Result;
import cn.bupt.sse.nmp.service.MuseumStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-10 15:11
 **/
@RestController
@RequestMapping("/statistic")
@Api("博物馆统计信息管理")
public class MuseumStatisticsController {
    @Autowired
    private MuseumStatisticsService statisticsService;
    //每层楼的各类活跃人的数量和总人数
    @PostMapping(value = "/personNum")
    @ApiOperation(value = "获取楼层的各类用户数量")
    public Result PersonNum(@RequestParam("buildingId") String buildingId,
                            @RequestParam("floor") Integer floor){
        Map<String,Integer> personNum = statisticsService.getPersonNum(buildingId,floor);
        return Result.success(personNum);
    }
    //返回当天的热门展品
    @PostMapping(value = "/topExhibition")
    @ApiOperation(value = "返回当天的热门展品")
    public Result topExhibition(@RequestParam(value = "number",defaultValue = "5") Integer number){
        List<Map<String, Integer>> topExhibition = statisticsService.topExhibition(number);
        return Result.success(topExhibition);
    }
    //返回活跃用户的列表
    @PostMapping(value = "/activeUser")
    @ApiOperation(value = "返回当前楼层的活跃用户")
    public Result activeUser(@RequestParam(value = "floor") Integer floor){
        List<String> userIds = statisticsService.activeUser(floor);
        return Result.success(userIds);
    }
    //返回选定用户的轨迹
    @PostMapping(value = "userTrace")
    @ApiOperation(value = "返回指定用户的轨迹")
    public Result userTrace(@RequestParam(value = "userId")Integer userId){
        List<String> trace = statisticsService.userTrace(userId);
        return Result.success(trace);
    }


}
