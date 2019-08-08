package promise.app_base.databinding;
import promise.app_base.R;
import promise.app_base.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class TodoLayoutBindingImpl extends TodoLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.appcompat.widget.LinearLayoutCompat mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TodoLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private TodoLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (android.widget.CheckedTextView) bindings[2]
            );
        this.checked.setTag(null);
        this.dateTextView.setTag(null);
        this.mboundView0 = (androidx.appcompat.widget.LinearLayoutCompat) bindings[0];
        this.mboundView0.setTag(null);
        this.titleText.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.todo == variableId) {
            setTodo((promise.app_base.models.Todo) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTodo(@Nullable promise.app_base.models.Todo Todo) {
        this.mTodo = Todo;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.todo);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        promise.app_base.models.Todo todo = mTodo;
        boolean todoCompleted = false;
        java.lang.String todoName = null;
        java.lang.String todoDate = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (todo != null) {
                    // read todo.completed()
                    todoCompleted = todo.completed();
                    // read todo.name()
                    todoName = todo.name();
                    // read todo.date()
                    todoDate = todo.date();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.checked, todoCompleted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.dateTextView, todoDate);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.titleText, todoName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): todo
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}