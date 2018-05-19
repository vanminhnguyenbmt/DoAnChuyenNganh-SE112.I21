<!DOCTYPE html>
<html>
<head>
    <?php
        include 'header.html';
     ?>
</head>
<body class="flat-blue">
    <div class="app-container">
        <div class="row content-container">
            <nav id="navbar" class="navbar navbar-default navbar-fixed-top navbar-top">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-expand-toggle">
                            <i class="fa fa-bars icon"></i>
                        </button>
                        <ol class="breadcrumb navbar-breadcrumb">
                            <li>UI Kits</li>
                            <li class="active">Theming</li>
                        </ol>
                        <button type="button" class="navbar-right-expand-toggle pull-right visible-xs">
                            <i class="fa fa-th icon"></i>
                        </button>
                    </div>
                    <ul class="nav navbar-nav navbar-right">
                        <button type="button" class="navbar-right-expand-toggle pull-right visible-xs">
                            <i class="fa fa-times icon"></i>
                        </button>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-comments-o"></i></a>
                            <ul class="dropdown-menu animated fadeInDown">
                                <li class="title">
                                    Notification <span class="badge pull-right">0</span>
                                </li>
                                <li class="message">
                                    No new notification
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown danger">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-star-half-o"></i> 4</a>
                            <ul class="dropdown-menu danger  animated fadeInDown">
                                <li class="title">
                                    Notification <span class="badge pull-right">4</span>
                                </li>
                                <li>
                                    <ul class="list-group notifications">
                                        <a href="#">
                                            <li class="list-group-item">
                                                <span class="badge">1</span> <i class="fa fa-exclamation-circle icon"></i> new registration
                                            </li>
                                        </a>
                                        <a href="#">
                                            <li class="list-group-item">
                                                <span class="badge success">1</span> <i class="fa fa-check icon"></i> new orders
                                            </li>
                                        </a>
                                        <a href="#">
                                            <li class="list-group-item">
                                                <span class="badge danger">2</span> <i class="fa fa-comments icon"></i> customers messages
                                            </li>
                                        </a>
                                        <a href="#">
                                            <li class="list-group-item message">
                                                view all
                                            </li>
                                        </a>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown profile">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Emily Hart <span class="caret"></span></a>
                            <ul class="dropdown-menu animated fadeInDown">
                                <li class="profile-img">
                                    <img src="../../img/profile/picjumbo.com_HNCK4153_resize.jpg" class="profile-img">
                                </li>
                                <li>
                                    <div class="profile-info">
                                        <h4 class="username">Emily Hart</h4>
                                        <p>emily_hart@email.com</p>
                                        <div class="btn-group margin-bottom-2x" role="group">
                                            <button type="button" class="btn btn-default"><i class="fa fa-user"></i> Profile</button>
                                            <button type="button" class="btn btn-default"><i class="fa fa-sign-out"></i> Logout</button>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>

            <?php
                include("siderbar.html");
            ?>

            <div class="container-fluid">
                <div class="side-body">
                    <div class="page-title">
                        <span class="title">Theming</span>
                        <div class="description">Color Scheme and Theme Layout Configuration</div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="card">
                                <div class="card-header">
                                    <div class="card-title">
                                        <div class="title">Theming</div>
                                    </div>
                                </div>
                                <div class="card-body">
                                  <p>Choose your navbar and sidebar color pattern. Default or Inverse Color.</p>
                                  <div class="sub-title">Navbar</div>
                                  <div>
                                    <div class="radio3 radio-check radio-inline">
                                      <input type="radio" id="radio1" name="radio-navbar" value="default" checked="">
                                      <label for="radio1">
                                        Default
                                      </label>
                                    </div>
                                    <div class="radio3 radio-check radio-inline">
                                      <input type="radio" id="radio2" name="radio-navbar" value="inverse">
                                      <label for="radio2">
                                        Inverse
                                      </label>
                                    </div>
                                  </div>
                                  <div class="sub-title">Sidebar</div>
                                  <div>
                                    <div class="radio3 radio-check radio-inline">
                                      <input type="radio" id="radio3" name="radio-sidebar" value="default">
                                      <label for="radio3">
                                        Default
                                      </label>
                                    </div>
                                    <div class="radio3 radio-check radio-inline">
                                      <input type="radio" id="radio4" name="radio-sidebar" value="inverse" checked="">
                                      <label for="radio4">
                                        Inverse
                                      </label>
                                    </div>
                                  </div>
                                  <div class="sub-title">Color Scheme</div>
                                  <div>
                                    <div class="radio3 radio-check radio-inline">
                                      <input type="radio" id="radio-blue" name="radio-color" value="blue" checked="">
                                      <label for="radio-blue">
                                        Blue (Default)
                                      </label>
                                    </div>
                                    <div class="radio3 radio-check radio-success radio-inline">
                                      <input type="radio" id="radio-green" name="radio-color" value="green">
                                      <label for="radio-green">
                                        Green
                                      </label>
                                    </div>
                                  </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer class="app-footer">
            <div class="wrapper">
                <span class="pull-right">2.1 <a href="#"><i class="fa fa-long-arrow-up"></i></a></span> © 2015 Copyright.
            </div>
        </footer>
    <div>

    <?php
        include 'footer.html';
     ?>

</body>

</html>
