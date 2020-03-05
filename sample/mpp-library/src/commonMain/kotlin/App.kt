/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import com.icerockdev.library.AppTheme
import com.icerockdev.library.MR
import com.icerockdev.library.SharedFactory
import com.icerockdev.library.sample.PostsScreen
import com.icerockdev.library.sample.PostsViewModel
import com.icerockdev.library.universal.CartScreen
import com.icerockdev.library.universal.InfoWebViewScreen
import com.icerockdev.library.universal.LoginScreen
import com.icerockdev.library.universal.LoginViewModel
import com.icerockdev.library.universal.PlatformProfileScreen
import com.icerockdev.library.universal.ProductScreen
import com.icerockdev.library.universal.ProductsScreen
import com.icerockdev.library.universal.ProfileScreen
import com.icerockdev.library.universal.WidgetsScreen
import dev.icerock.moko.graphics.Color
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.FlatAlertIds
import dev.icerock.moko.widgets.ImageWidget
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.TabsWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.container
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.factory.ButtonWithIconViewFactory
import dev.icerock.moko.widgets.factory.FloatingLabelInputViewFactory
import dev.icerock.moko.widgets.factory.IconGravity
import dev.icerock.moko.widgets.factory.LinearViewFactory
import dev.icerock.moko.widgets.factory.SystemImageViewFactory
import dev.icerock.moko.widgets.factory.SystemInputViewFactory
import dev.icerock.moko.widgets.factory.SystemTabsViewFactory
import dev.icerock.moko.widgets.factory.SystemTextViewFactory
import dev.icerock.moko.widgets.flat.FlatInputViewFactory
import dev.icerock.moko.widgets.sample.InputWidgetGalleryScreen
import dev.icerock.moko.widgets.sample.ProductsSearchScreen
import dev.icerock.moko.widgets.sample.ScrollContentScreen
import dev.icerock.moko.widgets.sample.SelectGalleryScreen
import dev.icerock.moko.widgets.sample.TabsSampleScreen
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.Screen
import dev.icerock.moko.widgets.screen.ScreenDesc
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.navigation.BottomNavigationItem
import dev.icerock.moko.widgets.screen.navigation.BottomNavigationScreen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.NavigationScreen
import dev.icerock.moko.widgets.screen.navigation.Resultable
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.createPushResultRoute
import dev.icerock.moko.widgets.screen.navigation.createPushRoute
import dev.icerock.moko.widgets.screen.navigation.createRouter
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.screen.TemplateScreen
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.state.PressableState
import dev.icerock.moko.widgets.style.state.SelectableState
import dev.icerock.moko.widgets.style.view.MarginValues
import dev.icerock.moko.widgets.style.view.PaddingValues
import dev.icerock.moko.widgets.style.view.TextHorizontalAlignment
import dev.icerock.moko.widgets.style.view.TextStyle
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.platformSpecific

class App() : BaseApplication() {

    object SystemInputId : InputWidget.Id
    object FloatingLabelInputId : InputWidget.Id
    object FlatInputId : InputWidget.Id

    override val androidStatusBarColor: Color? = Color(0x4444AAFF)

    override fun setup(): ScreenDesc<Args.Empty> {
        val theme = Theme {
            factory[ScrollContentScreen.Ids.ScrollContent] = LinearViewFactory(
                padding = PaddingValues(padding = 16.0f)
            )
            factory[SystemInputId] = SystemInputViewFactory(margins = MarginValues(bottom = 16.0f))
            factory[FloatingLabelInputId] = FloatingLabelInputViewFactory(margins = MarginValues(bottom = 16.0f))
            factory[FlatInputId] = FlatInputViewFactory(margins = MarginValues(bottom = 16.0f))
        }

        return registerScreen(RootNavigationScreen::class) {
            val router = createRouter()
            val widgetsTheme = Theme(theme) {
                factory[FlatAlertIds.Message] = SystemTextViewFactory(
                    textHorizontalAlignment = TextHorizontalAlignment.CENTER
                )
                factory[InputWidget.DefaultCategory] = SystemInputViewFactory(
                    textStyle = TextStyle(
                        size = 16,
                        color = Color(0x16171AFF)
                    )
                )
                factory[TabsWidget.DefaultCategory] = SystemTabsViewFactory(
                    tabsTintColor = Color(0xD20C0AFF),
                    tabsPadding = platformSpecific(android = null, ios = PaddingValues(padding = 16f)),
                    titleColor = SelectableState(
                        selected = Color(platformSpecific(android = 0x151515FF, ios = 0xFFFFFFFF)),
                        unselected = platformSpecific(android = Color(0x15151599), ios = null)
                    )
                )
            }
            val collectionTestScr = registerScreen(WidgetsScreen::class) {
                WidgetsScreen(SharedFactory(), widgetsTheme, AppTheme.PostsCollection)
            }

            val posts = PostsScreen(
                theme = theme,
                viewModel = PostsViewModel(),
                collectionCategory = AppTheme.PostsCollection
            )

            RootNavigationScreen(
                initialScreen = registerPostTestScreen(theme, createRouter()),
                router = router
            )
        }
    }

    private fun registerPostTestScreen(theme: Theme,
                                       router: NavigationScreen.Router
    ): TypedScreenDesc<Args.Empty, PostsScreen> {
        return registerScreen(PostsScreen::class) {
            PostsScreen(
                theme,
                PostsViewModel(),
                AppTheme.PostsCollection
            )
        }
    }

    private fun registerGalleryScreen(
        theme: Theme,
        router: NavigationScreen.Router
    ): TypedScreenDesc<Args.Empty, SelectGalleryScreen> {
        return registerScreen(SelectGalleryScreen::class) {
            SelectGalleryScreen(
                theme = theme,
                routes = listOf(
                    buildInputGalleryRouteInfo(theme, router),
                    buildSearchRouteInfo(theme, router),
                    buildTabsRouteInfo(theme, router),
                    SelectGalleryScreen.RouteInfo(
                        name = "Old Demo".desc(),
                        route = router.createPushRoute(oldDemo(router))
                    )
                )
            )
        }
    }

    private fun buildInputGalleryRouteInfo(
        theme: Theme,
        router: NavigationScreen.Router
    ): SelectGalleryScreen.RouteInfo {
        val inputScreen = registerScreen(InputWidgetGalleryScreen::class) {
            InputWidgetGalleryScreen(
                theme = theme,
                inputs = listOf(
                    InputWidgetGalleryScreen.InputInfo(
                        id = SystemInputId,
                        label = "SystemInputViewFactory".desc()
                    ),
                    InputWidgetGalleryScreen.InputInfo(
                        id = FloatingLabelInputId,
                        label = "FloatingLabelInputViewFactory".desc()
                    ),
                    InputWidgetGalleryScreen.InputInfo(
                        id = FlatInputId,
                        label = "FlatInputViewFactory".desc()
                    )
                )
            )
        }
        return SelectGalleryScreen.RouteInfo(
            name = "InputWidget".desc(),
            route = router.createPushRoute(inputScreen)
        )
    }

    private fun buildSearchRouteInfo(
        theme: Theme,
        router: NavigationScreen.Router
    ): SelectGalleryScreen.RouteInfo {
        val searchScreen = registerScreen(ProductsSearchScreen::class) {
            ProductsSearchScreen(theme)
        }

        return SelectGalleryScreen.RouteInfo(
            name = "SearchScreen".desc(),
            route = router.createPushRoute(searchScreen)
        )
    }

    private fun buildTabsRouteInfo(
        theme: Theme,
        router: NavigationScreen.Router
    ): SelectGalleryScreen.RouteInfo {
        val tabsTheme = Theme(theme) {
            TabsSampleScreen.configureDefaultTheme(this)
        }
        val tabsScreen = registerScreen(TabsSampleScreen::class) {
            TabsSampleScreen(tabsTheme)
        }

        return SelectGalleryScreen.RouteInfo(
            name = "Tabs".desc(),
            route = router.createPushRoute(tabsScreen)
        )
    }

    private fun oldDemo(
        router: NavigationScreen.Router
    ): TypedScreenDesc<Args.Empty, LoginScreen> {
        val sharedFactory = SharedFactory()
        val theme = AppTheme.baseTheme

        val loginTheme = Theme(AppTheme.loginScreen) {
            factory[LoginScreen.Id.EmailInputId] = FlatInputViewFactory(
                textStyle = TextStyle(
                    size = 16,
                    color = Color(0x16171AFF)
                ),
                backgroundColor = Color(0xF5F5F5FF)
            )
            factory[LoginScreen.Id.RegistrationButtonId] = ButtonWithIconViewFactory(
                icon = PressableState(all = MR.images.stars_black_18),
                iconGravity = IconGravity.TEXT_END,
                iconPadding = 8.0f,
                padding = PaddingValues(padding = 16f),
                background = PressableState(
                    normal = Background(fill = Fill.Solid(color = Color(0xAAFFFFFF))),
                    pressed = Background(fill = Fill.Solid(color = Color(0x88FFFFFF))),
                    disabled = Background(fill = Fill.Solid(color = Color(0x55FFFFFF)))
                )
            )
            factory[ImageWidget.DefaultCategory] = SystemImageViewFactory(
                cornerRadius = 16.0f
            )
        }

        val widgetsTheme = Theme(theme) {
            factory[FlatAlertIds.Message] = SystemTextViewFactory(
                textHorizontalAlignment = TextHorizontalAlignment.CENTER
            )
            factory[InputWidget.DefaultCategory] = SystemInputViewFactory(
                textStyle = TextStyle(
                    size = 16,
                    color = Color(0x16171AFF)
                )
            )
            factory[TabsWidget.DefaultCategory] = SystemTabsViewFactory(
                tabsTintColor = Color(0xD20C0AFF),
                tabsPadding = platformSpecific(android = null, ios = PaddingValues(padding = 16f)),
                titleColor = SelectableState(
                    selected = Color(platformSpecific(android = 0x151515FF, ios = 0xFFFFFFFF)),
                    unselected = platformSpecific(android = Color(0x15151599), ios = null)
                )
            )
        }

        val mainScreen = registerScreen(MainBottomNavigationScreen::class) {
            val bottomRouter = createRouter()

            val templateScreen = registerScreen(TemplateScreen::class) {
                TemplateScreen(navTitle = "Template".desc(), labelText = "Template Screen".desc(), theme = theme)
            }

            val cartNavigation = registerScreen(CartNavigationScreen::class) {
                val navigationRouter = createRouter()
                val profileScreen = registerScreen(PlatformProfileScreen::class) {
                    PlatformProfileScreen(navigationRouter.createPopRoute())
                }
                val cartScreen = registerScreen(CartScreen::class) {
                    CartScreen(
                        theme = theme,
                        profileRoute = router.createPushRoute(profileScreen) { ProfileScreen.Arg(it) }
                    )
                }
                CartNavigationScreen(
                    initialScreen = cartScreen,
                    router = navigationRouter
                )
            }

            val productsNavigation = registerScreen(ProductsNavigationScreen::class) {
                val navigationRouter = createRouter()

                val productScreen = registerScreen(ProductScreen::class) {
                    ProductScreen(
                        theme = theme,
                        cartRoute = bottomRouter.createChangeTabRoute(2)
                    )
                }
                val productsScreen = registerScreen(ProductsScreen::class) {
                    ProductsScreen(
                        theme = theme,
                        productRoute = navigationRouter.createPushRoute(productScreen) {
                            ProductScreen.Args(productId = it)
                        }
                    )
                }
                ProductsNavigationScreen(
                    initialScreen = productsScreen,
                    router = navigationRouter
                )
            }

            val widgetsScreen = registerScreen(WidgetsScreen::class) {
                WidgetsScreen(sharedFactory, widgetsTheme, AppTheme.PostsCollection)
            }

            val logoutScreen = registerScreen(LogoutScreen::class) {
                LogoutScreen(
                    theme = theme,
                    logoutRoute = router.createPopToRootRoute()
                )
            }

            MainBottomNavigationScreen(bottomRouter) {
                tab(
                    id = 1,
                    title = "Products".desc(),
                    selectedIcon = MR.images.cart_black_18,
                    unselectedIcon = MR.images.home_black_18,
                    screenDesc = productsNavigation
                )
                tab(
                    id = 2,
                    title = "Cart".desc(),
                    icon = MR.images.cart_black_18,
                    screenDesc = cartNavigation
                )
                tab(
                    id = 3,
                    title = "Widgets".desc(),
                    icon = MR.images.stars_black_18,
                    screenDesc = widgetsScreen
                )
                tab(
                    id = 4,
                    title = "Logout".desc(),
                    screenDesc = logoutScreen
                )
                tab(
                    id = 5,
                    title = "Empty".desc(),
                    screenDesc = templateScreen
                )
            }
        }

        val regScreen = registerScreen(RegisterScreen::class) {
            RegisterScreen(theme)
        }

        val oauthScreen = registerScreen(InfoWebViewScreen::class) {
            InfoWebViewScreen(
                theme = loginTheme
            )
        }

        return registerScreen(LoginScreen::class) {
            LoginScreen(
                theme = loginTheme,
                mainRoute = router.createPushRoute(mainScreen),
                registerRoute = router.createPushResultRoute(regScreen) { it.token },
                infoWebViewRoute = router.createPushRoute(oauthScreen) {
                    InfoWebViewScreen.WebViewArgs(it)
                }
            ) { LoginViewModel(it) }
        }
    }
}

class RegisterScreen(
    private val theme: Theme
) : WidgetScreen<Args.Empty>(), Resultable<RegisterScreen.Result>, NavigationItem {
    override val navigationBar: NavigationBar = NavigationBar.Normal(title = "Registration".desc())

    override var screenResult: Result? = null

    override fun createContentWidget() = with(theme) {
        container(size = WidgetSize.AsParent) {
            center {
                button(
                    size = WidgetSize.WrapContent,
                    content = ButtonWidget.Content.Text(Value.data("set result".desc()))
                ) {
                    screenResult = Result("my result")
                }
            }
        }
    }

    @Parcelize
    data class Result(val token: String) : Parcelable
}

class LogoutScreen(
    private val theme: Theme,
    private val logoutRoute: Route<Unit>
) : WidgetScreen<Args.Empty>() {
    override fun createContentWidget() = with(theme) {
        container(size = WidgetSize.AsParent) {
            center {
                button(
                    size = WidgetSize.WrapContent,
                    content = ButtonWidget.Content.Text(Value.data("logout".desc()))
                ) {
                    logoutRoute.route()
                }
            }
        }
    }
}

// TODO required for Android side... should be reworked if any ideas will be
class MainBottomNavigationScreen(
    router: Router,
    builder: BottomNavigationItem.Builder.() -> Unit
) : BottomNavigationScreen(router, builder), NavigationItem {
    override val navigationBar: NavigationBar = NavigationBar.None

    init {
        bottomNavigationColor = Color(0x6518f4FF)

        itemStateColors = SelectableState(
            selected = Color(0xfdfffdFF),
            unselected = Color(0xc0a3f9FF)
        )
    }
}

class RootNavigationScreen<T>(initialScreen: TypedScreenDesc<Args.Empty, T>, router: Router) :
    NavigationScreen<T>(initialScreen, router) where T : Screen<Args.Empty>, T : NavigationItem {
    override val androidStatusBarColor: Color? = Color(0x11AA11FF)
}

class ProductsNavigationScreen(initialScreen: TypedScreenDesc<Args.Empty, ProductsScreen>, router: Router) :
    NavigationScreen<ProductsScreen>(initialScreen, router)

class CartNavigationScreen(initialScreen: TypedScreenDesc<Args.Empty, CartScreen>, router: Router) :
    NavigationScreen<CartScreen>(initialScreen, router)

