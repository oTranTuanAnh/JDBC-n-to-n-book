package services;

import model.Category;

import java.util.List;

public interface ICategoryService extends IService<Category>{
List<Category> findAllByBookID(int b_id);
}
