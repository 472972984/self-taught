package indi.repo.springboot.design.factory;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 * @desc:
 */
public class BlackHuman implements Human{

    @Override
    public void getColor() {
        System.out.println("黑的皮肤是黑色的....");
    }

    @Override
    public void talk() {
        System.out.println("黑种人说话.....");
    }
}
