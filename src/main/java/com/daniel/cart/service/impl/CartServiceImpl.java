package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.CartOperateLog;
import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.enums.CartExceptionEnum;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.res.CartBlockRes;
import com.daniel.cart.domain.res.CartRes;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.CartVo;
import com.daniel.cart.exception.CartOperateException;
import com.daniel.cart.mapper.*;
import com.daniel.cart.service.CartService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

@Service("cartService")
@Transactional
public class CartServiceImpl implements CartService {
    private final CartMapper mapper;
    private final DepartmentMapper departmentMapper;
    private final CartOperateLogMapper cartOperateLogMapper;
    private final EmployeeMapper employeeMapper;

    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);


    @Autowired
    public CartServiceImpl(CartMapper mapper, DepartmentMapper departmentMapper, CartOperateLogMapper cartOperateLogMapper, EmployeeMapper employeeMapper) {
        this.mapper = mapper;
        this.departmentMapper = departmentMapper;
        this.cartOperateLogMapper = cartOperateLogMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Cart> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Cart> findAll(Integer start, Integer pageSize) {
        return findAllByLimit(null, null, null, start, pageSize);
    }

    @Override
    public List<Cart> findByDepartment(String departmentName) {
        return findByDepartment(departmentName, null, null);
    }

    @Override
    public List<Cart> findByDepartment(String departmentName, Integer start, Integer pageSize) {
        departmentNameCheck(departmentName);
        return findAllByLimit(null, departmentName, null, start, pageSize);
    }

    @Override
    public List<Cart> findByDepartment(Long departmentId) {
        return findByDepartment(departmentId, null, null);
    }

    @Override
    public List<Cart> findByDepartment(Long departmentId, Integer start, Integer pageSize) {
        departmentIdCheck(departmentId);
        return findAllByLimit(departmentId, null, null, start, pageSize);
    }

    @Override
    public List<Cart> findByState(String state) {
        return findByState(state, null, null);
    }

    @Override
    public List<Cart> findByState(String state, Integer start, Integer pageSize) {
        stateCheck(state);
        return findAllByLimit(null, null, CartStateEnum.valueOf(state), start, pageSize);
    }

    @Override
    public List<Cart> findByLimit(Long departmentId, String state) {
        return findByLimit(departmentId, state, null, null);
    }

    @Override
    public List<Cart> findByLimit(Long departmentId, String state, Integer start, Integer pageSize) {
        departmentIdCheck(departmentId);
        stateCheck(state);
        return findAllByLimit(departmentId, null, CartStateEnum.valueOf(state), start, pageSize);
    }

    @Override
    public List<Cart> findByLimit(String departmentName, String state) {
        return findAllByLimit(null, departmentName, CartStateEnum.valueOf(state), null, null);
    }

    @Override
    public List<Cart> findByLimit(String departmentName, String state, Integer start, Integer pageSize) {
        stateCheck(state);
        departmentNameCheck(departmentName);
        return findAllByLimit(null, departmentName, CartStateEnum.valueOf(state), start, pageSize);
    }

    @Override
    public Cart findById(Long id) {
        idCheck(id);
        return mapper.getById(id);
    }

    @Override
    public Long getCount() {
        return getCountByLimit(null, null, null);
    }

    @Override
    public Long getCountByState(String state) {
        stateCheck(state);
        return getCountByLimit(null, null, CartStateEnum.valueOf(state));
    }

    @Override
    public Long getCountByDepartment(Long departmentId) {
        departmentIdCheck(departmentId);
        return getCountByLimit(departmentId, null, null);
    }

    @Override
    public Long getCountByDepartment(String departmentName) {
        departmentNameCheck(departmentName);
        return getCountByLimit(null, departmentName, null);
    }

    @Override
    public Long getCountByLimit(Long departmentId, String state) {
        stateCheck(state);
        departmentIdCheck(departmentId);
        return getCountByLimit(departmentId, null, CartStateEnum.valueOf(state));
    }

    @Override
    public Long getCountByLimit(String departmentName, String state) {
        stateCheck(state);
        departmentNameCheck(departmentName);
        return getCountByLimit(null, departmentName, CartStateEnum.valueOf(state));
    }

    @Override
    public List<CartRes> getException(Map<CartExceptionEnum, HashSet<Long>> exceptionDrugMap){
        // 查找带有 block 列表的急救车信息
        List<CartBlockRes> cartWithBlocks = mapper.findCartWithBlock();
        return getException(cartWithBlocks, exceptionDrugMap);
    }

    @Override
    public List<CartRes> getException(List<CartBlockRes> cartWithBlocks, Map<CartExceptionEnum, HashSet<Long>> exceptionDrugMap){
        // 存放结果的列表
        List<CartRes> res = new ArrayList<>();

        // 获取到所有有空缺位置的急救车列表
        HashSet<Cart> vacant = mapper.findVacant();

        // 遍历列表
        for (CartBlockRes cartWithBlock : cartWithBlocks) {
            // 每个车对应一个异常 list
            List<String> exceptionList = new ArrayList<>();

            // 获取其中的 cart 信息，如果 cart 在 vacant 列表中，则当前 cart 有待补货的异常
            Cart cart = cartWithBlock.getCart();
            if(vacant.contains(cart)) {
                exceptionList.add(CartExceptionEnum.vacant.getName());
            }

            // 获取当前急救车的 block 列表
            List<Block> blockList = cartWithBlock.getBlockList();

            // 遍历参数中的异常列表
            for (CartExceptionEnum exceptionEnum : exceptionDrugMap.keySet()) {
                // 如果 cart 有任意一个 block 处在异常状态，就将异常状态存入 list 中
                HashSet<Long> exceptionDrugs = exceptionDrugMap.get(exceptionEnum);
                for (Block block : blockList) {
                    if(exceptionDrugs.contains(block.getDrugId())) {
                        exceptionList.add(exceptionEnum.getName());
                        break;
                    }
                }
            }

            // 将存在异常的 cart 封装好加入结果中
            if(!exceptionList.isEmpty()) {
                res.add(new CartRes(cart, exceptionList));
            }
        }
        return res;
    }

    @Override
    public Boolean add(Cart cart) {
        if(cart == null) {
            return false;
        }
        return mapper.addCart(cart) > 0;
    }

    @Override
    public Boolean modify(Cart cart) {
        if(cart != null && cart.getId() != null) {
            return mapper.modifyCart(cart) > 0;
        }
        return false;
    }

    @Override
    public Boolean remove(Long id) {
        idCheck(id);
        if(null == findById(id)) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "待删除的急救车信息不存在");
        }
        return mapper.removeById(id) > 0;
    }

    @Override
    public Boolean setCartFree(Long id) {
        return setCartState(id, CartStateEnum.free);
    }

    @Override
    public Boolean setCartInventory(Long id) {
        return setCartState(id, CartStateEnum.inventory);
    }

    @Override
    public Boolean setCartEmergency(Long id) {
        return setCartState(id, CartStateEnum.emergency);
    }

    @Override
    public Map<String, String> getAllStates() {
        Map<String, String> map = new HashMap<>();
        for (CartStateEnum value : CartStateEnum.values()) {
            map.put(value.name(), value.getName());
        }
        return map;
    }

    private Boolean setCartState(Long id, CartStateEnum state) {
        idCheck(id);
        Cart cart = findById(id);
        if(null == cart) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "待修改的急救车信息不存在");
        }
        if(cart.getState().equals(state)) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "待修改的急救车已处于该状态");
        }
        cart.setState(state);
        Boolean res =  mapper.modifyCart(cart) > 0;
        if(res) {
            CartOperateLog log = new CartOperateLog();
            log.setCart(cart);
            log.setOperateType(state);
            Subject subject = SecurityUtils.getSubject();
            String principal = (String)subject.getPrincipal();
            Employee employee = employeeMapper.findByPhone(principal);
            if(employee != null && employee.getId() != null) {
                log.setEmployeeId(employee.getId());
            } else {
                log.setEmployeeId(2L);
            }
            cartOperateLogMapper.add(log);
        }
        return res;
    }

    private Long getCountByLimit(Long departmentId, String nameCondition, CartStateEnum state) {
        CartVo limit = new CartVo();
        limit.setDepartmentId(departmentId);
        limit.setDepartmentName(nameCondition);
        limit.setState(state);
        return mapper.getCountByLimit(limit);
    }

    private List<Cart> findAllByLimit(Long departmentId, String nameCondition, CartStateEnum state, Integer start, Integer pageSize) {
        CartVo limit = new CartVo();
        limit.setDepartmentId(departmentId);
        limit.setDepartmentName(nameCondition);
        limit.setState(state);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findAllByLimit(limit);
    }

    private void idCheck(Long id) {
        if(!isIdOk(id)) {
            throw new CartOperateException(ResultCodeEnum.CART_OPERATE_ERROR.getCode(), "id 信息缺失");
        }
    }

    private void stateCheck(String state) {
        if(!isStringOk(state) || !EnumUtils.isValidEnum(CartStateEnum.class, state)) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "状态信息缺失");
        }
    }

    private void departmentNameCheck(String departmentName) {
        if(!isStringOk(departmentName)) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "部门名称信息缺失");
        }
    }

    private void departmentIdCheck(Long departmentId) {
        if(!isIdOk(departmentId)) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "部门 id 信息缺失");
        }
        if(departmentMapper.findById(departmentId) == null) {
            throw new CartOperateException(ResultCodeEnum.CART_QUERY_ERROR.getCode(), "对应部门信息不存在");
        }
    }


}
