package clwang.chunyu.me.wcl_dagger_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import clwang.chunyu.me.wcl_dagger_demo.contents.GitHubService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 代码库列表
 * <p>
 * Created by wangchenlong on 16/1/2.
 */
public class ReposListActivity extends Activity {
    @Bind(R.id.repos_rv_list) RecyclerView mRvList;

    @Inject
    GitHubService mGitHubService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_list);
        ButterKnife.bind(this);

        DemoApplication.component().inject(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(manager);

        ListAdapter adapter = new ListAdapter();
        mRvList.setAdapter(adapter);

        mGitHubService.getRepoData("SpikeKing")
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addRepo);
    }
}
