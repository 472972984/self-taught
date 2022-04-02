package indi.repo.springboot.design.factory;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 */
public class YellowHuman implements Human{

    @Override
    public void getColor() {
        System.out.println("黄种人的皮肤是黄色的....");
    }

    @Override
    public void talk() {
        System.out.println("黄种人说话.....");
    }
}
