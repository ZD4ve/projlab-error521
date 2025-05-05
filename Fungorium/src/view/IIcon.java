package view;

public interface IIcon {
    Object getIcon(); // Returns an icon or null if dead

    Class<?> getType(); // Returns the type of the item
}